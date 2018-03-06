export class Question {

  wording: string;

  suggestions: string[];

}

export class Quiz {

  id: number;

  questions: Question[];

}

export class QuizAnswer {

  answers: string[];

}

export class QuizResult {

  nbOfRightAnswers: number;

  totalNumberOfQuestions: number;

  message: string;

}
