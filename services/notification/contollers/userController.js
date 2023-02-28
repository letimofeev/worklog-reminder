const userService = require('../services/userService')
const {validationResult} = require("express-validator");
const ApiError = require("../errors/apiError");
const ValidationFailedApiSubError = require("../errors/validationFailedApiSubError");

class UserController {
    async create(request, response) {
        const user = request.body
        const createdUser = await userService.create(user)
        return response.json(createdUser)
    }

    async getAll(request, response) {
        const users = await userService.getAll()
        return response.json(users)
    }

    async getById(request, response, next) {
        const errors = validationResult(request);
        if (!errors.isEmpty()) {
            const validationFailedErrors = errors.array()
                .map(error => new ValidationFailedApiSubError(error))
            return next(ApiError.badRequest('Bad request', validationFailedErrors));
        }
        const {id} = request.params
        const user = await userService.getById(id)
        return response.json(user)
    }

    async update(request, response) {
        const user = request.body
        const rowsUpdated = await userService.update(user)
        return response.json({
            rowsUpdated: rowsUpdated
        })
    }

    async deleteById(request, response) {
        const {id} = request.params
        const rowsDeleted = await userService.deleteById(id)
        return response.json({
            rowsDeleted: rowsDeleted
        })
    }
}

module.exports = new UserController()
