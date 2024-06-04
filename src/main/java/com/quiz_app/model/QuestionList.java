package com.quiz_app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class QuestionList {
    private List<Question> questions;


}
