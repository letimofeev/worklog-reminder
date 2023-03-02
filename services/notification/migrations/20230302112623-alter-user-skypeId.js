'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
    await queryInterface.removeColumn('users', 'skype_id');

    await queryInterface.addColumn('users', 'login', {
      type: Sequelize.STRING(64),
      allowNull: true
    });
  },

  async down (queryInterface, Sequelize) {
    await queryInterface.removeColumn('users', 'login');

    await queryInterface.addColumn('users', 'skypeId', {
      field: 'skype_id',
      type: Sequelize.STRING(64),
      allowNull: true
    });
  }
};
