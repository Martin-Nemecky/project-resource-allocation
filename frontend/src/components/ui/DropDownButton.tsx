import { ReactElement } from "react";

interface IDropDownButton {
    mainTitle : string,
    items : ReactElement[]
}

export default function DropDownButton({mainTitle, items} : IDropDownButton) {

    return (
        <div className="dropdown">
            <button className="btn btn-secondary dropdown-toggle rounded-pill" type="button" id="dropdownMenu2" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                {mainTitle}
            </button>
            <div className="dropdown-menu" aria-labelledby="dropdownMenu2">
                {items.map(item => item)}
            </div>
        </div>
    );
}