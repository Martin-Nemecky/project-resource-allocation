import { useStatus } from "../contexts/StatusContext";

export default function Alert() {
    const { status, setStatus } = useStatus();

    return (
        <div className="d-flex flex-row">
                {status.show &&
                    <div className={"alert " + status.type + " alert-dismissible fade show flex-grow-1 m-0"} role="alert">
                        <strong>{status.message}</strong>
                        <button type="button" className="btn-close" aria-label="Close" onClick={() => setStatus(prev => {
                            return { ...prev, show: false }
                        })}></button>
                    </div>
                }
        
        </div>
    );
}