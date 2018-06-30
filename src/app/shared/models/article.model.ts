export interface Article {
  id: string;
  title:string;
  subtitle:string;
  category:any;
  description:string;
  tags?: string[];

  photo?: string;
  gallery?: any[];

  status: string;
  price:number;
  ean?: string;
  latitude?: number;
  longitude?: number;
  currency: string;
  brand:string;
  model: string;
  condition:string;
  userSeller: any;
  userBuyer: any;
  rating: number;
  ratingCount: number;
  hashIPFS?: string;
}
