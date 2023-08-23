package com.nguyen.capstonecrm.helper;

import com.nguyen.capstonecrm.model.Report;
import javafx.collections.ObservableList;

/**
 * Functional Interface for reading files
 */
public interface OutputReportInterface {

    /**
     * <p>An abstract method for a functional interface</p>
     *
     * @return ObservableList<Report>
     * @throws Exception
     */
    ObservableList<Report> outputReport() throws Exception;

}
