export class ApiError {
    status: number;
    message: string;
    errors: any[];

    constructor(status, message, errors = []) {
        this.status = status
        this.message = message
        this.errors = errors
    }

    static badRequest(message, errors = []) {
        return new ApiError(400, message, errors)
    }

    static notFound(message, errors = []) {
        return new ApiError(404, message, errors)
    }

    static conflict(message, errors = []) {
        return new ApiError(409, message, errors)
    }

    static internalServerError(message, errors = []) {
        return new ApiError(500, message, errors)
    }
}
