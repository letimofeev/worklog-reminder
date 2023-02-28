const {Sequelize} = require('sequelize')

module.exports = new Sequelize(
    process.env.POSTGRES_DB || 'notification',
    process.env.POSTGRES_USER || 'postgres',
    process.env.POSTGRES_PASS || 'postgres',
    {
        dialect: 'postgres',
        host: process.env.POSTGRES_HOST || 'localhost',
        port: process.env.POSTGRES_PORT || 5433
    }
)
