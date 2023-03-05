export class DeleteUserResponseDto {
    constructor(rowsDeleted: number) {
        this.rowsDeleted = rowsDeleted;
    }

    rowsDeleted: number;
}
