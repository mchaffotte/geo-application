export enum QuestionType {
  CAPITAL, TOTAL_AREA
}

export class QuizConfiguration {

  questionType: QuestionType;

}

export class Question {

  wording: string;

  suggestions: string[];

}

export class Quiz {

  id: number;

  questions: Question[];

}

export class QuizAnswer {

  answers: string[] = [];

}

export class QuizResult {

  nbOfCorrectAnswers: number;

  nbOfQuestions: number;

}
