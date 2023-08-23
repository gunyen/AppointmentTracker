package com.nguyen.capstonecrm.helper;

import com.nguyen.capstonecrm.DAO.Query;
import com.nguyen.capstonecrm.controller.CustomersController;
import com.nguyen.capstonecrm.model.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Holds methods that handle time operations
 */
public abstract class TimeHandler {

    /**
     * <p>Populates the set comboBox with times in increments of 15 inputting translated designated time window from EST to the users machine local time</p>
     *
     * @param comboBox the comboBox to set
     */
    public static void fillTimeBox(ComboBox<LocalTime> comboBox) {
        ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));

        while (zonedDateTimeStart.toLocalTime().isBefore(zonedDateTimeEnd.toLocalTime().plusSeconds(1))) {
            comboBox.getItems().add(zonedDateTimeStart.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime());
            zonedDateTimeStart = zonedDateTimeStart.plusMinutes(15);
        }
    }

    /**
     * <p>Returns a boolean value based on whether the set startTime and/or set endTime is out of bounds of designated time window</p>
     *
     * @param startTime the startTime to set
     * @param endTime   the endTime to set
     * @return boolean value
     */
    public static boolean verifyBusinessHours(Timestamp startTime, Timestamp endTime) {
        ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));

        LocalTime start = startTime.toLocalDateTime().toLocalTime();
        LocalTime end = endTime.toLocalDateTime().toLocalTime();
        LocalTime startEST = zonedDateTimeStart.withZoneSameInstant(LocalDateTime.now().atZone(ZoneId.systemDefault()).getZone()).toLocalTime();
        LocalTime endEST = zonedDateTimeEnd.withZoneSameInstant(LocalDateTime.now().atZone(ZoneId.systemDefault()).getZone()).toLocalTime();

        if (start.isBefore(startEST) || start.isAfter(endEST) || end.isBefore(startEST) || end.isAfter(endEST)) {
            return false;
        } else if (startTime.toLocalDateTime().getDayOfWeek().equals(DayOfWeek.SATURDAY) || startTime.toLocalDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return false;
        }
        return true;
    }

    /**
     * <p>Returns a boolean value based on set startTime and/or endTime if an appointment object already occupies a designate time window</p>
     *
     * @param startTime the startTime to set
     * @param endTime   the endTime to set
     * @return
     * @throws SQLException
     */
    public static boolean appointmentOverlapCheck(Timestamp startTime, Timestamp endTime) throws SQLException {
        String sql = "SELECT * FROM appointments";
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            if (startTime.after(start) && startTime.before(end)) {
                Alerts.alertWarning("Appointment Overlap","You have another appointment during this time.\nNew appointments can't start at the same time or\nin the middle of an existing appointment.");
                return false;
            } else if (endTime.after(start) && endTime.before(end)) {
                Alerts.alertWarning("Appointment Overlap","You have another appointment during this time.\nNew appointments can't end at the same time or\nin the middle of an existing appointment.");
                return false;
            } else if (startTime.before(start) && endTime.after(end)) {
                Alerts.alertWarning("Appointment Overlap","You have another appointment during this time.\nNew appointments can't have an existing\nappointment within selected window of time.");
                return false;
            } else if(startTime.equals(start) || endTime.equals(end)) {
                if(CustomersController.createApp) {
                    Alerts.alertWarning("Appointment Overlap","You have another appointment during this time.\nNew appointments can't start and/or end at the same time\nas an existing appointment.");
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * <p>Returns boolean value depending on an Appointment object if the object's start time is within a 15 minute time window from users machine current time</p>
     * <p>
     * The return value of true alerts the user that there is an Appointment object within the 15 minute time window. The return value of false alerts that there isn't an Appointment object within the 15 minutre time window.
     * </p>
     *
     * @param allAppointments the allAppointments to set
     * @return
     */
    public static boolean meetingAlert(ObservableList<Appointment> allAppointments) {
        for (int i = 0; i < allAppointments.size(); i++) {
            LocalTime start = allAppointments.get(i).getStartDateTime().toLocalDateTime().toLocalTime();
            LocalTime current = LocalTime.now();
            long gap = ChronoUnit.MINUTES.between(current, start);
            if (gap <= 15 && start.isAfter(LocalTime.now())) {
                int appId = allAppointments.get(i).getAppointmentId();
                LocalDate date = allAppointments.get(i).getStartDateTime().toLocalDateTime().toLocalDate();
                LocalTime starttime = allAppointments.get(i).getStartDateTime().toLocalDateTime().toLocalTime();
                LocalTime endtime = allAppointments.get(i).getEndDateTime().toLocalDateTime().toLocalTime();
                Alerts.alertInfo("Upcoming Appointment",("You have an appointment in " + gap + " minute(s)\n" + "Appointment ID: " + appId + "\nDate:  " + date + "\nTime: " + starttime + " - " + endtime));
                return true;
            }
        }
        Alerts.alertInfo("Upcoming Appointment","You have no upcoming appointments");
        return false;
    }
}
