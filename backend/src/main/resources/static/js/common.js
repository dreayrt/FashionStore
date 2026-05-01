/**
 * Common JS for Fashion Store
 * Handles automatic button disabling during AJAX requests to improve UX
 */
(function() {
    // Helper to disable/enable element
    function setElementLoading(el, isLoading) {
        if (!el) return;
        
        // Only target buttons or links that look like buttons
        const isButton = el.tagName === 'BUTTON' || (el.tagName === 'A' && el.classList.contains('btn'));
        if (!isButton) return;

        if (isLoading) {
            // Store original state if not already stored
            if (el.dataset.isLoading === 'true') return;
            
            el.dataset.isLoading = 'true';
            el.dataset.originalHtml = el.innerHTML;
            el.dataset.originalDisabled = el.disabled;
            
            el.disabled = true;
            el.style.pointerEvents = 'none';
            el.style.opacity = '0.7';
            
            // Add a small spinner if it's a relatively large button and doesn't have one
            if (el.offsetWidth > 40 && !el.querySelector('.spinner-border')) {
                // Use a span to wrap the spinner for better styling
                const spinner = '<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>';
                el.innerHTML = spinner + el.innerHTML;
            }
        } else {
            if (el.dataset.isLoading !== 'true') return;
            
            el.disabled = el.dataset.originalDisabled === 'true';
            el.style.pointerEvents = 'auto';
            el.style.opacity = '1';
            if (el.dataset.originalHtml) {
                el.innerHTML = el.dataset.originalHtml;
            }
            el.dataset.isLoading = 'false';
        }
    }

    // Intercept Fetch API
    const originalFetch = window.fetch;
    window.fetch = function(...args) {
        const activeElement = document.activeElement;
        // Check if the call is actually an AJAX request (not a static resource or something)
        // Usually, we want to disable buttons for all fetch calls initiated by user interaction
        setElementLoading(activeElement, true);

        return originalFetch(...args).finally(() => {
            setElementLoading(activeElement, false);
        });
    };

    // Intercept XMLHttpRequest (for older libraries or specific cases)
    const originalSend = XMLHttpRequest.prototype.send;

    XMLHttpRequest.prototype.send = function() {
        // Capture active element at the moment of send
        const el = document.activeElement;
        setElementLoading(el, true);
        
        this.addEventListener('loadend', () => {
            setElementLoading(el, false);
        });
        
        return originalSend.apply(this, arguments);
    };

})();
