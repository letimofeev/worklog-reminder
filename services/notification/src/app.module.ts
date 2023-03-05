import {Module} from '@nestjs/common';
import {UserModule} from "./modules/users/user.module";
import {BotModule} from "./modules/bots/bot.module";
import {NotificationModule} from "./modules/notifications/notification.module";

@Module({
    imports: [
        UserModule,
        BotModule,
        NotificationModule
    ]
})
export class AppModule {}
