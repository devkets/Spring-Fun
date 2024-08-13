package com.devkets.springtesting.Services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.devkets.springtesting.Models.SudokuRequestModel;
import com.devkets.springtesting.Models.SudokuResponseModel;

@Service
public class SudokuService {

    private final int MAX_VALID = 45;
    private final int MATRIX_DIMENSION = 9;

    public SudokuResponseModel validateSudokuMatrix(SudokuRequestModel request) {
        SudokuResponseModel response = new SudokuResponseModel();
        response.setMessage("Valid sudoku puzzle!");
        response.setRequest(request);

        if(request == null) {
            response.setMessage("Input was null.");
            return response;
        }

        List<String> matrixList = request.getLines();

        if(!checkForValidInput(matrixList, response)){
            return response;
        }

        int[][] sudokuMatrix = parseMatrix(matrixList);

        if(!checkHorizontals(sudokuMatrix)) {
            response.setMessage("Horizontals did not pass inspection.");
            return response;
        }
        if(!checkVerticals(sudokuMatrix)) {
            response.setMessage("Verticals did not pass inspection.");
            return response;
        }
        if(!checkSubMatrices(sudokuMatrix)) {
            response.setMessage("Sub 3x3s did not pass inspection.");
            return response;
        }
        return response;
    }

    public SudokuResponseModel carlinsValidationSpecial(SudokuRequestModel request) {
        SudokuResponseModel response = new SudokuResponseModel();
        response.setRequest(request);

        List<String> matrixList = request.getLines();

        boolean rowsValid = true;
        boolean columnsValid = true;
        boolean subMatrixValid = true;

        //check horizontals
        for(String i : matrixList){
            if(!checkValidString(i)){
                rowsValid = false;
            }
        }
        
        //check verticals
        for (int i = 0; i < 9; i++){
            StringBuilder sb = new StringBuilder();
            for (String j : matrixList){
                sb.append(j.charAt(i));
            }
            if (!checkValidString(sb.toString())){
                columnsValid = false;
            }
        }

        //check sub-matrices
        int[] subMatrixSums = new int[9];
        checkSubMatrixList(matrixList, subMatrixSums);

        for (int i : subMatrixSums) {
            if (i != 45) {
                subMatrixValid = false;
            }
        }

        if (rowsValid) {
            if (columnsValid) {
                if (subMatrixValid){
                    response.setMessage("Valid puzzle");
                } else {
                    response.setMessage("Sub matrix invalid");
                }
            } else {
                response.setMessage("Columns invalid");
            }
        } else {
            response.setMessage("Rows invalid");
        }
        return response;
    }

    //Private functions------------------------------------------------------
    //-----------------------------------------------------------------------

    private boolean checkForValidInput(List<String> matrixList, SudokuResponseModel response){
        if(matrixList.size() != MATRIX_DIMENSION){
            response.setMessage("Invalid request, each column must have 9 digits.");
            return false;
        }
        for(String i : matrixList) {
            if(i.length() != MATRIX_DIMENSION){
                response.setMessage("Invalid request, each row must have 9 digits.");
                return false;
            }
            if(!i.matches("[0-9]+")){
                response.setMessage("Invalid request, only numbers are allowed.");
                return false;
            }
        }
        return true;
    }

    private void checkSubMatrixList(List<String> matrixList, int[] subMatrixSums) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++){
                int charValue = Character.getNumericValue(matrixList.get(i).charAt(j));
                if (i < 3) {
                    if (j < 3) {
                        subMatrixSums[0] += charValue;
                    } else if (j < 6) {
                        subMatrixSums[1] += charValue;
                    } else {
                        subMatrixSums[2] += charValue;
                    }
                } else if (i < 6) {
                    if (j < 3) {
                        subMatrixSums[3] += charValue;
                    } else if (j < 6) {
                        subMatrixSums[4] += charValue;
                    } else {
                        subMatrixSums[5] += charValue;
                    }
                } else {
                    if (j < 3) {
                        subMatrixSums[6] += charValue;
                    } else if (j < 6) {
                        subMatrixSums[7] += charValue;
                    } else {
                        subMatrixSums[8] += charValue;
                    }
                }
            }
        }
    }

    private boolean checkValidString(String row){
        int sum = 0;
        for(int j = 0; j < row.length(); j++){
            sum += Character.getNumericValue(row.charAt(j));
        }
        if (sum == MAX_VALID) {
            return true;
        }
        return false;
    }

    private int[][] parseMatrix(List<String> data) {
        
        int[][] matrix = new int[9][9];

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                matrix[i][j] = Character.getNumericValue(data.get(i).charAt(j));
            }
        }
        return matrix;
    }

    private boolean checkHorizontals(int[][] matrix) {
        int horizontalSum;

        for(int j = 0; j < 9; j++) {
            horizontalSum = 0;
            for(int k = 0; k < 9; k++) {
                horizontalSum += matrix[j][k];
            }
            if(horizontalSum != MAX_VALID) {
                return false;
            }
        }
        return true;
    }

    private boolean checkVerticals(int[][] matrix) {
        int verticalSum;
        for(int j = 0; j < 9; j++) {
            verticalSum = 0;
            for(int k = 0; k < 9; k++) {
                verticalSum += matrix[k][j];
            }
            if(verticalSum != MAX_VALID) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSubMatrices(int[][] matrix) {
        int subMatrixSum;
        int x = 0;
        int y = 0;

        while(x < 9) {
            subMatrixSum = 0;
            for(int i = x; i < x+3; i++){
                for(int j = y; j < y+3; j++) {
                    subMatrixSum += matrix[i][j];
                }
            }
            if(subMatrixSum != MAX_VALID) {
                return false;
            }
            x += 3;
            if(x == 9){
                x = 0;
                y += 3;
                if(y == 9){
                    x = 9;
                }
            }
        }
        return true;
    }
}