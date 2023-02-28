const User = require("../models/user");

class UserService {
    async createUser(user) {
        return User.create(user);
    }

    async getAllUsers() {
        return User.findAll()
    }

    async getUserById(id) {
        return User.findByPk(id)
    }

    async updateUser(user) {
        const {id, ...columnsToUpdate} = user
        return User.update(columnsToUpdate, {
            where: {
                id: id
            }
        }).then(value => value[0])
    }

    async deleteUserById(id) {
        return User.destroy({
            where: {
                id: id
            }
        })
    }
}

module.exports = new UserService()
