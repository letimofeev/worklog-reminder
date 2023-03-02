require('./models/user')
const express = require('express')
const sequelize = require('./database')
const router = require('./routes/index')
const uncaughtErrorHandler = require('./middleware/uncaughtErrorHandler')
const apiErrorHandler = require("./middleware/apiErrorHandler");
const {uniqueConstraintErrorHandler} = require("./middleware/databaseErrorHandler");
const {Sequelize} = require("sequelize");
const Umzug = require('umzug');

const umzug = new Umzug({
    migrations: {
        path: './migrations',
        params: [
            sequelize.getQueryInterface(),
            Sequelize
        ]
    },
    storage: 'sequelize',
    storageOptions: {
        sequelize: sequelize
    }
});

const PORT = process.env.PORT || 8200

const app = express()

app.use(express.json())
app.use('/api', router)
app.use(apiErrorHandler)
app.use(uniqueConstraintErrorHandler)
app.use(uncaughtErrorHandler)

const start = async () => {
    try {
        await umzug.up();
        console.log('All migrations have been executed');
        await sequelize.authenticate()
        app.listen(PORT, () => console.log(`\nServer started on port ${PORT}`))
    } catch (e) {
        console.log(e)
    }
}

start()
