package com.devkets.springtesting.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devkets.springtesting.Models.PostRequestModel;
import com.devkets.springtesting.Models.PostResponseModel;
import com.devkets.springtesting.Models.SudokuRequestModel;
import com.devkets.springtesting.Services.PostService;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping(value="/retrieveJSON")
    public ResponseEntity<PostResponseModel> retrieveJSON(@RequestBody PostRequestModel request) {

        PostResponseModel response = new PostResponseModel();
        response.setAccountNumber(request.getAccountNumber());
        response.setMessage("Successful passthrough");

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value="/verifySudoku")
    public ResponseEntity<PostResponseModel> verifySudoku(@RequestBody SudokuRequestModel request) {

        PostResponseModel response = new PostResponseModel();
        response = postService.validateSudokuMatrix(request);

        return ResponseEntity.ok().body(response);
    }
}
