export class filterBody {
  constructor(

    public minDate: number,
    public minMonth: number,
    public minYear: number,
    public maxDate: number,
    public maxMonth: number,
    public maxYear: number,

    public minDateInb: number,
    public minMonthInb: number,
    public minYearInb: number,
    public maxDateInb: number,
    public maxMonthInb: number,
    public maxYearInb: number,

    public genehmigt: boolean,
    public inbetrieb: boolean
    ){
  }
}
