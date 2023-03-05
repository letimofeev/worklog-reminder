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
                    response = NotificationResponseDto.failed(user.login, 'User disabled')
                }
                this.logger.log(`Notification response: ${response.message}`)
                return JSON.stringify(response);
            })
        );
    }

    private async sendNotification(user: User, message: string): Promise<NotificationResponseDto> {
        const conversationReference = user.conversationReference as Partial<ConversationReference>;
        let notificationResponse;
        try {
            await this.adapter.continueConversationAsync(botConfig.MicrosoftAppId, conversationReference,
                async (context) => {
                    const response = await context.sendActivity(message);
                    if (response.id) {
                        notificationResponse = NotificationResponseDto.pass(user.login);
                    } else {
                        this.logger.warn(`Something went wrong, bot notification response ` +
                            `has not present response id (${response.id})`);
                        notificationResponse = NotificationResponseDto.failed(user.login);
                    }
                })
        } catch (e) {
            this.logger.error(`Error during sending notification\n ${e.stack}`);
            notificationResponse = NotificationResponseDto.failed(user.login);
        }
        return notificationResponse;
    }
}
