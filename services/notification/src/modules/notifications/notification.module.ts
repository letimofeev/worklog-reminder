import {Module} from "@nestjs/common";
import {UserModule} from "../users/user.module";
import {NotificationService} from "./notification.service";
import {NotificationController} from "./notification.controller";
import {BotModule} from "../bots/bot.module";

@Module({
    controllers: [NotificationController],
    providers: [NotificationService],
    exports: [NotificationService],
    imports: [UserModule, BotModule]
})
export class NotificationModule {}