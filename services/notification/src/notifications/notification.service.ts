import {Injectable, Logger} from '@nestjs/common';
import {from, mergeMap, Observable} from "rxjs";
import {ActivityHandler, CloudAdapter} from "botbuilder";
import {UserService} from "../users/user.service";
import {User} from "../users/user.model";
import {NotificationResponseDto} from "./dtos/notification-response.dto";
import {ConversationReference} from "botframework-schema";
import {botConfig} from "../bots/bot.config";
import {NotificationDto} from "./dtos/notification.dto";

@Injectable()
export class NotificationService {
    private readonly logger = new Logger(NotificationService.name);

    constructor(private adapter: CloudAdapter,
                private activityHandler: ActivityHandler,
                private userService: UserService) {
    }

    async sendNotifications(notifications: NotificationDto[]): Promise<Observable<string>> {
        console.log(notifications)
        const notificationsMap = new Map(notifications.map(notification => [
            notification.login, notification.message
        ]))
        const logins = Array.from(notificationsMap.keys());
        const users = await this.userService.findAllByLogins(logins)
        return from(users).pipe(
            mergeMap(async (user: User) => {
                let response;
                if (user.enabled) {
                    const message = notificationsMap.get(user.login);
                    response = await this.sendNotification(user, message);
                } else {
                    response = new NotificationResponseDto(user.login, 'User disabled')
                }
                return JSON.stringify(response);
            })
        );
    }

    private async sendNotification(user: User, message: string): Promise<NotificationResponseDto> {
        const conversationReference = user.conversationReference as Partial<ConversationReference>;
        await this.adapter.continueConversationAsync(botConfig.MicrosoftAppId, conversationReference,
            async (context) => {
                await context.sendActivity(message);
            })
        return new NotificationResponseDto(user.login, 'Notification sent successfully');
    }
}
