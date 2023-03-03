'use strict';

const {DataTypes} = require("sequelize");

module.exports = {
    async up(queryInterface, Sequelize) {
        await queryInterface.createTable('users', {
            id: {
                type: DataTypes.INTEGER,
                primaryKey: true,
                autoIncrement: true
            },
            skypeId: {
                field: 'skype_id',
                type: DataTypes.STRING(64),
                unique: true,
                allowNull: false
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
        })
    },

    async down(queryInterface, Sequelize) {
        await queryInterface.dropTable('users');
    }
};
