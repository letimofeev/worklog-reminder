export class UpdateUserDto {
    id: number;
    skypeId: string;
    login: string;
    displayName: string;
    enabled: boolean;
    conversationReference: JSON
}
