import {NestFactory} from '@nestjs/core';
import * as process from 'process';
import {AppModule} from './app.module';
import {Sequelize} from "sequelize";
import * as Umzug from "umzug";
import {ValidationPipe} from "./pipes/validation.pipe";
import {UniqueConstraintErrorFilter} from "./exceptions/handlers/unique-constraint-error.filter";

async function runMigrations(umzug) {
    const pendingMigrations = await umzug.pending();
    if (pendingMigrations.length === 0) {
        console.log('No pending migrations');
        return;
    }

    console.log(`Pending migrations: ${pendingMigrations.length}`);
    await umzug.up();
    console.log('Migrations done');
}

async function bootstrap() {
    const PORT = process.env.PORT || 8200;
    const app = await NestFactory.create(AppModule);

    const sequelize = app.get('SEQUELIZE');
    const umzug = new Umzug({
        migrations: {
            path: './db/migrations',
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
    await runMigrations(umzug)

    app.useGlobalPipes(new ValidationPipe())
    app.useGlobalFilters(new UniqueConstraintErrorFilter())

    await app.listen(PORT, () => console.log(`Server stated on port = ${PORT}`));
}

bootstrap();
