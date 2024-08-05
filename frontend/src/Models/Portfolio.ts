export type PortfolioGet = {
    id: number;
    symbol: string;
    purchase: number;
    dividend: number;
    industry: string;
    marketCap: number;
    companyName: string;
    commentDtos: any;
  };
  
  export type PortfolioPost = {
    symbol: string;
  };