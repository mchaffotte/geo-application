export const enum QuestionType {
  CAPITAL = 'CAPITAL',
  TOTAL_AREA = 'TOTAL_AREA'
}

export class QuizConfiguration {

  questionType: QuestionType;

  multipleChoice: boolean;

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
