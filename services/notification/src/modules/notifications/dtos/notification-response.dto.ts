export class NotificationResponseDto {
    userId: string;
    message: string;

    constructor(userId: string, message: string) {
        this.userId = userId;
        this.message = message;
    }
}
