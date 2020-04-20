export const enum QuestionType {
  CAPITAL = 'CAPITAL',
  FLAG = 'FLAG',
  LAND_AREA = 'LAND_AREA',
  SILHOUETTE = 'SILHOUETTE',
  TOTAL_AREA = 'TOTAL_AREA',
  WATER_AREA = 'WATER_AREA',
}

export enum AnswerType {
  ANSWER = 'ANSWER',
  MULTIPLE_CHOICE = 'MULTIPLE_CHOICE',
}

export class QuizType {
  questionType: QuestionType;

  answerTypes: AnswerType[];
}

export class QuizConfiguration {
  questionType: QuestionType;

  answerType: AnswerType;
}

export class Question {
  imagePath: string;

  wording: string;

  choices: string[];
}

export class Quiz {
  id: number;

  questions: Question[];
}

export class QuestionAnswer {
  answers: string[] = [];
}

export class QuizAnswer {
  questionAnswers: QuestionAnswer[] = [];
}

export class QuizResult {
  nbOfCorrectAnswers: number;

  nbOfQuestions: number;
}
