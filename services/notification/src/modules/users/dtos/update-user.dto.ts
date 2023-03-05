import {IsInt, IsNotEmpty, Length, Min} from "class-validator";

export class UpdateUserDto {
    @IsNotEmpty({message: 'id is required'})
    @IsInt({message: 'id must be a integer'})
    @Min(1, {message: 'id value must be a positive'})
    id: number;

    @IsNotEmpty({message: 'skypeId is required'})
    @Length(1, 64, {message: 'skypeId must be less or equal than 64 symbols'})
    skypeId: string;

    login: string;

    @IsNotEmpty({message: 'displayName is required'})
    @Length(1, 64, {message: 'displayName must be less or equal than 64 symbols'})
    displayName: string;

    enabled: boolean;
    conversationReference: JSON
}
