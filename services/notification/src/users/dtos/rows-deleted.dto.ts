export class RowsDeletedDto {
    constructor(rowsDeleted: number) {
        this.rowsDeleted = rowsDeleted;
    }

    rowsDeleted: number;
}
