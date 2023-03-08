export class DayWorklogDebt {
    date: string;
    requiredSeconds: number;

    constructor(date: string, requiredSeconds: number) {
        this.date = date;
        this.requiredSeconds = requiredSeconds;
    }
}
