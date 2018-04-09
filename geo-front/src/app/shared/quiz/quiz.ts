export const enum QuestionType {
  CAPITAL = 'CAPITAL',
  FLAG = 'FLAG',
  TOTAL_AREA = 'TOTAL_AREA',
  SILHOUETTE = 'SILHOUETTE'
}

export class QuizConfiguration {

  questionType: QuestionType;

  multipleChoice: boolean;

}

export class Question {

  imagePath: string;

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
