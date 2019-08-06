interface IBook {
  _id: string;
  isbn: string;
  title: string;
  author: string;
  description: string;
  published_year: string;
  publisher: string;
  updated_date: Date;
}

interface IErrMsg {
  status: number;
  errMsg: string;
}
