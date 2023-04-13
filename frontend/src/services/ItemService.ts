import { ItemDto } from "../data/dtoTypes";

export function findById<T extends ItemDto>(id : number, items : T[]) : T | undefined{
    return items.find(i => i.id === id);
}

export function findByIds<T extends ItemDto>(ids : number[], items : T[]) : T[]{
    return items.filter(item => ids.includes(item.id));
}

