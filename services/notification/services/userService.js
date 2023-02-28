const User = require("../models/user");

class UserService {
    async create(user) {
        return User.create(user);
    }

    async getAll() {
        return User.findAll()
    }

    async getById(id) {
        return User.findByPk(id)
    }

    async update(user) {
        const {id, ...columnsToUpdate} = user
        return User.update(columnsToUpdate, {
            where: {
                id: id
            }
        }).then(value => value[0])
    }

    async deleteById(id) {
        return User.destroy({
            where: {
                id: id
            }
        })
    }
}

module.exports = new UserService()
