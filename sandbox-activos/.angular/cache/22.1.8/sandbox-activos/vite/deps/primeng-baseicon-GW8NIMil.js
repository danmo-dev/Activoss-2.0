import { Dr as ViewEncapsulation, In as Input, O as booleanAttribute, Wi as setClassMetadata, an as ChangeDetectionStrategy, as as ɵɵprojectionDef, cn as Component, is as ɵɵprojection, to as ɵɵdefineComponent } from "./core-CK5_Em9Y.js";
import { _ as ObjectUtils } from "./primeng-api-hFM0rYSV.js";
//#region node_modules/primeng/fesm2022/primeng-baseicon.mjs
var _c0 = ["*"];
var BaseIcon = class BaseIcon {
	label;
	spin = false;
	styleClass;
	role;
	ariaLabel;
	ariaHidden;
	ngOnInit() {
		this.getAttributes();
	}
	getAttributes() {
		const isLabelEmpty = ObjectUtils.isEmpty(this.label);
		this.role = !isLabelEmpty ? "img" : void 0;
		this.ariaLabel = !isLabelEmpty ? this.label : void 0;
		this.ariaHidden = isLabelEmpty;
	}
	getClassNames() {
		return `p-icon ${this.styleClass ? this.styleClass + " " : ""}${this.spin ? "p-icon-spin" : ""}`;
	}
	static ɵfac = function BaseIcon_Factory(__ngFactoryType__) {
		return new (__ngFactoryType__ || BaseIcon)();
	};
	static ɵcmp = /* @__PURE__ */ ɵɵdefineComponent({
		type: BaseIcon,
		selectors: [["ng-component"]],
		hostAttrs: [
			1,
			"p-element",
			"p-icon-wrapper"
		],
		inputs: {
			label: "label",
			spin: [
				2,
				"spin",
				"spin",
				booleanAttribute
			],
			styleClass: "styleClass"
		},
		ngContentSelectors: _c0,
		decls: 1,
		vars: 0,
		template: function BaseIcon_Template(rf, ctx) {
			if (rf & 1) {
				ɵɵprojectionDef();
				ɵɵprojection(0);
			}
		},
		encapsulation: 2
	});
};
(() => {
	(typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(BaseIcon, [{
		type: Component,
		args: [{
			template: ` <ng-content></ng-content> `,
			standalone: true,
			changeDetection: ChangeDetectionStrategy.OnPush,
			encapsulation: ViewEncapsulation.None,
			host: { class: "p-element p-icon-wrapper" }
		}]
	}], null, {
		label: [{ type: Input }],
		spin: [{
			type: Input,
			args: [{ transform: booleanAttribute }]
		}],
		styleClass: [{ type: Input }]
	});
})();
//#endregion
export { BaseIcon as t };
