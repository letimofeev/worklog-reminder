import { IsNotEmpty, IsOptional, Length } from "class-validator";

export class CreateUserDto {
    @IsNotEmpty({message: 'skypeId is required'})
    @Length(1, 128, {message: 'skypeId must be less or equal than 128 symbols'})
    skypeId: string;

    login: string;

    @IsOptional()
    @Length(1, 64, {message: 'displayName must be less or equal than 64 symbols'})
    displayName: string;

    enabled: boolean;
    conversationReference: JSON
}
