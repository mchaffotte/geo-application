export const enum QuestionType {
  CAPITAL = 'CAPITAL',
  FLAG = 'FLAG',
  LAND_AREA = 'LAND_AREA',
  SILHOUETTE = 'SILHOUETTE',
  TOTAL_AREA = 'TOTAL_AREA',
  WATER_AREA = 'WATER_AREA'
}

export enum ResponseType {
  ANSWER = 'ANSWER',
  MULTIPLE_CHOICE = 'MULTIPLE_CHOICE'
}

export class QuizType {

  questionType: QuestionType;

  responseTypes: ResponseType[];

}

export class QuizConfiguration {

  questionType: QuestionType;

  responseType: ResponseType;

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

export class QuizResponse {

  answers: string[] = [];

}

export class QuizResult {

  nbOfCorrectAnswers: number;

  nbOfQuestions: number;

}
