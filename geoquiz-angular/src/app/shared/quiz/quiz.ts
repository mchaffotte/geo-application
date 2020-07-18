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

export interface FilterType {
  label: string;
  name: string;
  values: Array<Possibility>;
}

export interface Possibility {
  label: string;
  value: number | string;
}

export interface Filter {
  name: string;
  value: number | string;
}

export interface QuizType {
  questionType: QuestionType;

  answerTypes: AnswerType[];

  filter: FilterType;
}

export interface QuizConfiguration {
  questionType: QuestionType;

  answerType: AnswerType;

  filter?: Filter;
}

export interface Question {
  imagePath?: string;

  wording: string;

  choices: string[];
}

export interface Quiz {
  id: number;

  questions: Question[];
}

export class QuestionAnswer {
  answers: string[] = [];
}

export class QuizAnswer {
  questionAnswers: QuestionAnswer[] = [];
}

export interface QuizResult {
  nbOfCorrectAnswers: number;

  nbOfQuestions: number;
}
