import {Column, Model, Table} from "sequelize-typescript";
import {DataTypes} from "sequelize";

interface UserCreationAttributes {
    skypeId: string
}

@Table({
    tableName: 'users',
    timestamps: false
})
export class User extends Model<User, UserCreationAttributes> {
    @Column({
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    })
    id: number;

    @Column({
        field: 'skype_id',
        type: DataTypes.STRING(64),
        unique: true,
        allowNull: false
    })
    skypeId: string;

    @Column({
        type: DataTypes.STRING(64),
        unique: true
    })
    login: string;

    @Column({
        field: 'display_name',
        type: DataTypes.STRING(64),
    })
    displayName: string;

    @Column({
        type: DataTypes.BOOLEAN,
        defaultValue: true
    })
    enabled: boolean;

    @Column({
        field: 'conversation_reference',
        type: DataTypes.JSONB
    })
    conversationReference: JSON
}