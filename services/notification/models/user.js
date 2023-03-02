const sequelize = require('../database')
const {DataTypes} = require('sequelize')

const User = sequelize.define('user', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    login: {
        type: DataTypes.STRING(64),
        unique: true
    },
    displayName: {
        field: 'display_name',
        type: DataTypes.STRING(64),
        allowNull: false
    },
    enabled: {
        type: DataTypes.BOOLEAN,
        defaultValue: true
    },
    conversationReference: {
        field: 'conversation_reference',
        type: DataTypes.JSONB
    }
}, {
    timestamps: false
})

module.exports = User
