export class CreateUserDto {
    skypeId: string;
    login: string;
    displayName: string;
    enabled: boolean;
    conversationReference: JSON
}