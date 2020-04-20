export enum AlertType {
  SUCCESS = 'success',
  INFO = 'info',
  WARNING = 'warning',
  DANGER = 'danger',
  PRIMARY = 'primary',
  SECONDARY = 'secondary',
}

export class Alert {
  type: AlertType;
  message: string;
}
