package com.nguyen.c195.helper;/*
C195
Created by: John Nguyen
Creation Date: 6/29/2023
Creation Time: 3:12 PM
*/

import com.nguyen.c195.model.Report;
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
