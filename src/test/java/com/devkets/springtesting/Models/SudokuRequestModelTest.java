package com.devkets.springtesting.Models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRequestModelTest {

    @Test
    void getLines() {
        //Assemble
        SudokuRequestModel testModel = new SudokuRequestModel();
        List<String> testList = new ArrayList<>();
        testList.add("test1");
        testList.add("test2");
        testModel.setLines(testList);

        //Assert
        assertEquals(testModel.getLines(), testList);
    }
}