export class RowsDeletedResponseDto {
    constructor(rowsDeleted: number) {
        this.rowsDeleted = rowsDeleted;
    }

    rowsDeleted: number;
}
