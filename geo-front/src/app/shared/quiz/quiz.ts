export const enum QuestionType {
  CAPITAL = 'CAPITAL',
  FLAG = 'FLAG',
  LAND_AREA = 'LAND_AREA',
  SILHOUETTE = 'SILHOUETTE',
  TOTAL_AREA = 'TOTAL_AREA',
  WATER_AREA = 'WATER_AREA'
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
