class ApiError extends Error{
    constructor(status, message, errors = []) {
        super()
        this.status = status
        this.message = message
        this.errors = errors
    }

    static badRequest(message, errors = []) {
        return new ApiError(400, message, errors)
    }

    static conflict(message, errors = []) {
        return new ApiError(409, message, errors)
    }

    static internalServerError(message, errors = []) {
        return new ApiError(500, message, errors)
    }
}

module.exports = ApiError
