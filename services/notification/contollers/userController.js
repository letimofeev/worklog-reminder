const userService = require('../services/userService')
const ApiError = require("../errors/apiError");

class UserController {
    async create(request, response, next) {
        const user = request.body
        return userService.create(user)
            .then(createdUser => response.json(createdUser))
            .catch(error => next(error))
    }

    async getAll(request, response, next) {
        return userService.getAll()
            .then(users => response.json(users))
            .catch(error => next(error))
    }

    async getById(request, response, next) {
        const {id} = request.params
        return userService.getById(id)
            .then(user => {
                if (user == null) {
                    throw ApiError.notFound(`User with id = ${id} not found`)
                }
                return response.json(user)
            })
            .catch(error => next(error))
    }

    async update(request, response, next) {
        const user = request.body
        return userService.update(user)
            .then(rowsUpdated => response.json({
                rowsUpdated: rowsUpdated
            }))
            .catch(error => next(error))
    }

    async deleteById(request, response, next) {
        const {id} = request.params
        return userService.deleteById(id)
            .then(rowsDeleted => response.json({
                rowsDeleted: rowsDeleted
            }))
            .catch(error => next(error))
    }
}

module.exports = new UserController()
