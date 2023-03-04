import {HttpException} from "@nestjs/common";

export class UserNotFoundException extends HttpException {
    constructor(response) {
        super(response, response.status);
    }
}
