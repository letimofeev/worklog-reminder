import {Module} from '@nestjs/common';
import {UserModule} from "./users/user.module";
import {BotModule} from "./bots/bot.module";
import {NotificationModule} from "./notifications/notification.module";

@Module({
    imports: [
        UserModule,
        BotModule,
        NotificationModule
    ]
})
export class AppModule {}
