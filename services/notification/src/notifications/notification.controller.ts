import {Body, Controller, Post, Sse} from '@nestjs/common';
import {NotificationService} from './notification.service';
import {Observable} from "rxjs";
import {NotificationDto} from "./dtos/notification.dto";

@Controller('/api/notifications')
export class NotificationController {
    constructor(private notificationService: NotificationService) {
    }

    @Post()
    @Sse()
    sendNotifications(@Body() notifications: NotificationDto[]): Promise<Observable<string>> {
        return this.notificationService.sendNotifications(notifications);
    }
}
