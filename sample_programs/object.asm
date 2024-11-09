	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 15, 0
	.globl	_main
	.p2align	2
_main:
	.cfi_startproc
	sub	sp, sp, #96
	stp	x29, x30, [sp, #80]
	.cfi_def_cfa_offset 96
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	add	x2, sp, #20
	mov	w0, #0
	mov	w1, #15
	bl	_queens
	str	x0, [sp]
Lloh0:
	adrp	x0, l_.string@PAGE
Lloh1:
	add	x0, x0, l_.string@PAGEOFF
	bl	_printf
	mov	w0, #0
	ldp	x29, x30, [sp, #80]
	add	sp, sp, #96
	ret
	.loh AdrpAdd	Lloh0, Lloh1
	.cfi_endproc

	.globl	_queens
	.p2align	2
_queens:
	.cfi_startproc
	sub	sp, sp, #80
	stp	x22, x21, [sp, #32]
	stp	x20, x19, [sp, #48]
	stp	x29, x30, [sp, #64]
	.cfi_def_cfa_offset 80
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	stp	w1, w0, [sp, #24]
	cmp	w0, w1
	b.ne	LBB1_2
	mov	w8, #1
	str	w8, [sp, #20]
	b	LBB1_9
LBB1_2:
	str	xzr, [sp, #16]
	ldr	w20, [sp, #24]
	cbz	w20, LBB1_9
	mov	x19, x2
	b	LBB1_6
LBB1_4:
	ldrb	w8, [sp, #11]
	cbz	w8, LBB1_8
LBB1_5:
	ldr	w8, [sp, #16]
	add	w8, w8, #1
	str	w8, [sp, #16]
	cmp	w8, w20
	b.hs	LBB1_9
LBB1_6:
	strb	wzr, [sp, #11]
	str	wzr, [sp, #12]
	ldr	w8, [sp, #28]
	cbz	w8, LBB1_4
LBB1_7:
	ldrb	w9, [sp, #11]
	ldp	w11, w10, [sp, #12]
	sxtw	x11, w11
	ldr	w12, [x19, x11, lsl #2]
	sub	w13, w12, w10
	ldr	w14, [sp, #28]
	sub	w15, w11, w14
	sub	w14, w14, w11
	cmp	w13, w14
	ccmp	w13, w15, #4, ne
	ccmp	w12, w10, #4, ne
	cset	w10, eq
	orr	w9, w9, w10
	and	w9, w9, #0x1
	strb	w9, [sp, #11]
	add	w9, w11, #1
	str	w9, [sp, #12]
	cmp	w9, w8
	b.lo	LBB1_7
	b	LBB1_4
LBB1_8:
	ldp	w8, w21, [sp, #16]
	ldp	w1, w9, [sp, #24]
	sxtw	x9, w9
	str	w8, [x19, x9, lsl #2]
	add	w0, w9, #1
	mov	x2, x19
	bl	_queens
	add	w8, w21, w0
	str	w8, [sp, #20]
	b	LBB1_5
LBB1_9:
	ldr	w0, [sp, #20]
	ldp	x29, x30, [sp, #64]
	ldp	x20, x19, [sp, #48]
	ldp	x22, x21, [sp, #32]
	add	sp, sp, #80
	ret
	.cfi_endproc

	.section	__TEXT,__cstring,cstring_literals
l_.string:
	.asciz	"%d\n"

.subsections_via_symbols
