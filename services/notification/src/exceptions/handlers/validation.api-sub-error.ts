import {ApiSubError} from "./api-sub-error";

export class ValidationApiSubError extends ApiSubError {
    private rejectedValue: any;

    constructor(message, rejectedValue) {
        super(message);
        this.rejectedValue = rejectedValue;
    }
}
