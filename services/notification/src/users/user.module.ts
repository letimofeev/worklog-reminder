import {Module} from "@nestjs/common";
import {UserController} from "./user.controller";
import {UserService} from "./user.service";
import {userProviders} from "./user.provider";
import {databaseProviders} from "../databases/database.providers";
import {DatabaseModule} from "../databases/database.module";

@Module({
    controllers: [UserController],
    providers: [
        UserService,
        ...userProviders,
        ...databaseProviders
    ],
    exports: [UserService],
    imports: [DatabaseModule]
})
export class UserModule {}
